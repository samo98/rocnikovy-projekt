(ns rocnikovy-projekt.logged-bar
    (:require [rocnikovy-projekt.cursors :refer [logged-user-cursor]]))

(defn logged-bar []
  [:div {:class "LoggedBar"} 
    [:div "Logged as " (:name @logged-user-cursor)]
    [:a {:class "LoggedBar__logout"} "Logout"]])
