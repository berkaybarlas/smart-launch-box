from __future__ import print_function
import boto3
from decimal import Decimal
import json
import urllib

dynamodb = boto3.client('dynamodb')
s3 = boto3.client('s3', region_name='us-west-2')
rekognition = boto3.client('rekognition')


# --------------- Helper Functions ------------------

def detectLabels(bucket, fileName):
    response = rekognition.detect_labels( Image={'S3Object':
            {'Bucket':bucket,
            'Name':fileName, #key
            }}, MinConfidence=60)
    return response
    
def update_index(tableName,deviceId,tryCount,Name):
    response = dynamodb.update_item(
        TableName=tableName,
        Key={
            'device_id': {'S': deviceId},
            'timestamp': {'S': tryCount},
            },
            UpdateExpression='SET Food = :val1',
            ExpressionAttributeValues={
                ':val1': {'S' : Name}
            }
        ) 
    
# --------------- Main handler ------------------

def lambda_handler(event, context):
    
    # Get the object from the event
    bucket = 'besinler' # Bucket name
   
    key = urllib.unquote_plus(event['Records'][0]['s3']['object']['key'].encode('utf8'))
    tryNumber = key[11:-4]
    try:
        
        # Calls Amazon Rekognition IndexFaces API to detect faces in S3 object 
        # to index faces into specified collection
        
        response = detectLabels(bucket, key)
        
        # Commit faceId and full name object metadata to DynamoDB
        
        if response['ResponseMetadata']['HTTPStatusCode'] == 200:
            #faceId = response['FaceRecords'][0]['Face']['FaceId']
            FoodName = detectLabels(bucket, key)[u'Labels'][0]['Name'] 
            print (FoodName)
            update_index('box-data','kap1',tryNumber,FoodName)
            

        return response
    except Exception as e:
        print(e)
        print("Error processing object {} from bucket {}. ".format(key, bucket))
        raise e
        
