class UrlMappings {

	static mappings = {
       /* "/$controller/$action?/$id?(.$format)?"{
            constraints {
                // apply constraints here
            }
        }*/

        "/"(view:"/index")
        "500"(view:'/error')
		
		"/question"(resource:'question')
		
		"/question/get-all"{
			controller =  "question"
			action = "getQuestions"
			parseRequest= true
			method = "GET"
		}
		
		"/question/$id"{
			controller =  "question"
			action = "getQuestion"
			parseRequest= true
			method = "GET"
		}
		
		"/question/location/$id"{
			controller =  "question"
			action = "getQuestionByLocation"
			parseRequest= true
			method = "GET"
		}
		
		"/answer"(resource:'answer')
		
		"/question/query"{
			controller =  "question"
			action = "sendQuery"
			parseRequest= true
			method = "POST"
		}
		
		"/question/test"{
			controller = "question"
			action = "test"
		}
		
		"/answer/$id"{
			controller =  "answer"
			action = "getAnswerWithQuestionId"
			parseRequest= true
			method = "GET"
		}
		
		
		
	}
}
