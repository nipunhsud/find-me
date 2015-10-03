package com.findme.inbound

class UserController {
	def encryptionService

    def index() { }
	
	def save(){
		log.info "Save"
		log.info "Params "+ request.JSON.findme
		log.info "Requets "+ request.JSON
		User user = new User()
		user.deviceId = "qwd"
		user.username = "nipunsud"
		user.password = "testingpassword"
		user.email = "testing emails"
		user.currentLongitude = "ew"
		user.currentLatitude = "few"
		user.userId = "342klhf"
		//user.encryptedPassword = encryptionService.encrypt("Tasty")
		log.info "Encrypted "+ encryptionService.encrypt("Tasty")
		//user.id = "wqeqwe12434"
		
		if(user.save(flush: true,failOnError: true)){
				log.info "Saved"+user.id
				log.info "New user "+ user.get(user.id)
			}
		
		render(status:200, text: "User created", context:"application/json")
	}
	
	def createIndex(){
		log.info "Params "+ request.JSON
		log.info "long"
		log.info "lat"
		log.info "userId"
		log.info "Preference"
	}
}
