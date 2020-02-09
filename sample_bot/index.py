# /index.py

from flask import Flask, request, jsonify, render_template
import os
import dialogflow
import requests
import json
import pusher
import wikipedia

app = Flask(__name__)

@app.route('/')
def index():
    return render_template('index.html')

# run Flask app
if __name__ == "__main__":
    app.run()

@app.route('/know_disease', methods=['POST'])
def know_disease():
    data=request.get_json(silent=True)
    if data["queryResult"]["action"]=="know_disease":
        response=data["queryResult"]["parameters"]["disease"]
        info=""
        if response=="typhoid" or response=="dengue":
            info=wikipedia.summary(str(response)+" fever")
        elif "disease" not in str(response):
            query=str(response)+" "+"disease"
            info=wikipedia.summary(query)
        else:
            info=wikipedia.summary(response)
        reply = {
            "fulfillmentText": info,
        }
    elif data["queryResult"]["action"]=="loc_disease_predict":
        disease=data["queryResult"]["parameters"]["disease"]
        admin_area=data["queryResult"]["parameters"]["location"]["admin-area"]
        city=data["queryResult"]["parameters"]["location"]["city"]
        time=data["queryResult"]["parameters"]["present_or_future"]
        if admin_area:
            reply = {
            "fulfillmentText":str(disease)+" "+str(admin_area)+" "+"present",
        }
        else:
            reply = {
            "fulfillmentText": str(disease)+" "+str(city)+" "+"future",
        }
    else:
         reply = {
            "fulfillmentText": "Sorry, not supported yet",
        }
    return jsonify(reply)


def detect_intent_texts(project_id, session_id, text, language_code):
    session_client = dialogflow.SessionsClient()
    session = session_client.session_path(project_id, session_id)

    if text:
        text_input = dialogflow.types.TextInput(
            text=text, language_code=language_code)
        query_input = dialogflow.types.QueryInput(text=text_input)
        response = session_client.detect_intent(
            session=session, query_input=query_input)

        return response.query_result.fulfillment_text

@app.route('/send_message', methods=['POST'])
def send_message():
    message = request.form['message']
    project_id = os.getenv('DIALOGFLOW_PROJECT_ID')
    fulfillment_text = detect_intent_texts(project_id, "unique", message, 'en')
    response_text = { "message":  fulfillment_text }

    return jsonify(response_text)