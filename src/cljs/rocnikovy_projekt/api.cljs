(ns rocnikovy-projekt.api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(def success-codes [200 201])

(defn make-remote-call [route callback]
  (go (let [response (<! (http/get (str "/api" route)))]
        (if (some #(= (:status response) %) success-codes)
          (callback (:body response))))))

(defn make-post-request 
  ([route options callback]
   (go (let [response (<! (http/post (str "/api" route) options))]
          (if (some #(= (:status response) %) success-codes)
            (callback (:body response))))))
  ([route options callback error-callback]
   (go (let [response (<! (http/post (str "/api" route) options))]
          (if (some #(= (:status response) %) success-codes)
            (callback (:body response))
            (error-callback response))))))
