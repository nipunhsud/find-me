package com.findme.inbound


import grails.converters.deep.JSON
import groovy.json.JsonOutput

import org.apache.lucene.spatial.*

class QuestionController {
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def scaffold = Question
    def index() { }
	
	/**
	 * POST a new question/query
	 * 
	 * {findme :{
    "deviceId": "xyz823",
    "longitude": "86.4",
    "latitude": "23.2",
    "userId": "549wqr",
    "query": "What time does the mall close"
    
}
}
	 * 
	 * @return
	 */
	def save(){
		log.info "Save"
		log.info "Params "+ request.JSON.findme.longitude
		log.info "Requets "+ request.JSON
		def questionRequest = request.JSON.findme
		log.info "Request "+ questionRequest
		Question question = new Question()
		question.setUserId(request.JSON.findme.userId)
		question.setDeviceId(request.JSON.findme.deviceId)
		question.setLatitude(request.JSON.findme.longitude)
		question.setLongitude(request.JSON.findme.longitude)
		question.setQuery(request.JSON.findme.query)
		question.setTimeStamp(request.JSON.findme.timeStamp)

		question.save(flush: true,failOnError: true)
		log.info "Question id "+ question.id
<<<<<<< HEAD
		//getSpatialLocation()
		getUsersInProximity()
		render(status: 200, text: "Success", contextType: "application/json")
	}
	//questionId, spatialLocation
	def getUsersInProximity(){
		locationService.searchMe()
		//locationService.findUsersInProximity(spatialLocation)
	}
=======
		render(status: 200, text: "Success", contextType: "application/json")
	}
>>>>>>> parent of e0d640f... updates to q contlr,
	
	/*def sendQuery(){
		log.info "Params "+ params
		log.info "Requets "+ request.JSON
		
		Question question = new Question()
		question.setUserId("dwq")
		question.setDeviceId("dsa")
		question.setLatitude("45.6")
		question.setLongitude("54.6")
		question.setQuery("wheres my car")
		question.save(fail: true)
		render(status: 200, text: "Success", contextType: "application/json")
	} */
	
	/**
	 * GET a question by Id
	 * http://localhost:8080/findme/question/55bcfcd1d4c627aa998aeaf9
	 * @return
	 */
	def getQuestionById(){
		log.info "Params "+ params.id
		log.info "Requets "+ request.JSON
		if(params){
			log.info "Entered"
			def question = Question.findById(params.id)
			render(status:200, text: question as JSON , contextType: "application/json")
		}
		else{
			render(status:400, text: "Please send id", contextType: "application/text")
		}
		
		
	}
	
	/**
	 * GET all questions
	 * http://localhost:8080/findme/question/get-all
	 * @return
	 */
	def getQuestions(){
		log.info "Test"
		def questions = Question.findAll()
		log.info "Question "+ questions
		render(status:200, text: questions as JSON, contextType: "application/json")
	}
	
	/**
	 * GET a question by location
	 * Input-  lat and long
	 * @return
	 */
	def  getQuestionByLocation(){
		log.info "Params "+ params
		log.info "Requets "+ request.JSON
		if(params){
			render(status:200, text: JsonOutput.toJson())
		}
		else{
			render(status:400, text: "Please send id")
		}
	}
	
	def test(){
		log.info "Here we are"
	}
}
