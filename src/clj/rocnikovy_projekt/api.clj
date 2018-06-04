(ns rocnikovy-projekt.api
  (:require [compojure.core :refer [GET POST routes]]
            [ring.util.response :refer [response not-found]]
            [ring.util.http-response :refer [unauthorized]]
            [rocnikovy-projekt.database :refer [schools users session_tokens]]
            [rocnikovy-projekt.helpers :refer [generate-token]]
            [korma.core :refer [select where insert values join delete]]))

(def expiration-time 86400000)

(defn api [] 
  (routes
    (GET "/schools" [] (response (select schools)))
    (GET "/schools/:id" [id]
      (let [school (select schools (where {:id (Integer/parseInt id)}))]
        (if (empty? school)
          (not-found (str "No school with id " id " found"))
          (response (first school)))))
    (POST "/login" {{username :username password :password} :body}
      (let [user (select users (where {:name username :password password}))]
        (if (empty? user)
          (unauthorized "Username and password don't match")
          (let [newSessionToken {:token (generate-token)
                                 :userid (:id (first user))
                                 :createdat (System/currentTimeMillis) 
                                 :expiresat (+ (System/currentTimeMillis) expiration-time)}]
            (insert session_tokens (values newSessionToken))
            ;; set expiration time
            {:body {:user (first user)} :cookies {"token" {:value (:token newSessionToken)}}}))))
    (GET "/token-login" {{token "token"} :cookies}
      (if (some? token)
        (let [user (select users
                    (join session_tokens (= :session_tokens.userid :id))
                    (where {:session_tokens.token (Integer/parseInt (:value token))}))]
          (if (empty? user)
            {}
            (response {:user (first user)})))
        {}))
    (GET "/logout" {{token "token"} :cookies}
      (if (some? token)
        (delete session_tokens (where {:token (Integer/parseInt (:value token))})))
      {:cookies {"token" {:value "" :max-age 0}}})))
      
        
