package com.findme.inbound

import com.findme.inbound.Question

class QuestionController {
	static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]
	def scaffold = Question
    def index() { }
	
	def save(){
		log.info "Save"
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
	}
	
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
	
	def test(){
		log.info "Here we are"
	}
}
