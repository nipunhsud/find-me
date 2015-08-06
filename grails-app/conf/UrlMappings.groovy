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
