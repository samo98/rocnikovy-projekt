(ns rocnikovy-projekt.api
  (:require [compojure.core :refer [GET POST routes]]
            [ring.util.response :refer [response not-found]]
            [rocnikovy-projekt.database :refer [database]]))

(defn api [] 
  (routes
    (GET "/schools" [] (response (:schools database)))
    (GET "/schools/:id" [id]
      (let [school ((keyword id) (:schools database))]
        (if (nil? school)
          (not-found (str "No school with id " id " found"))
          (response school))))
    (POST "/" req (response (:body req)))))
