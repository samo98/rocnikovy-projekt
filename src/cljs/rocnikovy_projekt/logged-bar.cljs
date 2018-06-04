(ns rocnikovy-projekt.logged-bar
    (:require [rocnikovy-projekt.cursors :refer [logged-user-cursor]]
              [rocnikovy-projekt.api :refer [make-remote-call]]))

;; -------------------------
;; Actions

(defn logout []
  (make-remote-call "/logout"
    (fn []
      (reset! logged-user-cursor nil))))

(defn logged-bar []
  [:div {:class "LoggedBar"} 
    [:div "Logged as " (:name @logged-user-cursor)]
    [:a {:class "LoggedBar__logout" :on-click logout} "Logout"]])
