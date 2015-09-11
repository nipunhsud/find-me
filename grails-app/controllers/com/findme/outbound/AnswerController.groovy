package com.findme.outbound

import grails.converters.JSON

class AnswerController {

    def index() { }
	
	def save(){
		if(request.JSON){
			def json = request.JSON
			Answer answer = new Answer()
			answer.setQuestionId(json.questionId)
			answer.setUserId(json.userId)
			answer.setDeviceId(json.deviceId)
			answer.setLatitude(json.latitude)
			answer.setLongitude(json.logitude)
			answer.save(flush: true,failOnError: true)
			log.info "Answer with ${answer.id} created for ${json.questionId}"
			render(status: 200, text: answer.id as JSON, contextType: "application/json")
		}else{
			render(status: 404, text: "Please enter the Question Id", contextType: "application/json")
		}
	}
	
	def getAnswersWithQuestion() {
		if(params.id){
			//Update to get answers based on a location
			def answers = Answer.get(params.id)
			render(status: 200, text: answers as JSON , contextType: "application/json")
		}
	}
	
	
}
