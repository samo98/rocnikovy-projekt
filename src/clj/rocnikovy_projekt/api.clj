(ns rocnikovy-projekt.api
  (:require [compojure.core :refer [GET POST routes]]
            [ring.util.response :refer [response not-found]]
            [rocnikovy-projekt.database :refer [schools]]
            [korma.core :refer [select where]]))

(defn api [] 
  (routes
    (GET "/schools" [] (response (select schools)))
    ;;TODO check if string is parsable
    (GET "/schools/:id" [id]
      (let [school (select schools (where {:id (read-string id)}))]
        (if (empty? school)
          (not-found (str "No school with id " id " found"))
          (response (first school)))))
    (POST "/" req (response (:body req)))))
