(ns rocnikovy-projekt.api
  (:require [compojure.core :refer [GET POST routes]]
            [ring.util.response :refer [response]]))

(defn api [] 
  (routes
    (GET "/" [] (response {:message "message"}))
    (POST "/" req (response (:body req)))))
