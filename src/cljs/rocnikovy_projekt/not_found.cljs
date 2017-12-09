(ns rocnikovy-projekt.not-found)

(defn not-found-page []
  [:div {:class "NotFound"} 
   [:div {:class "NotFound__title"} "404 PAGE NOT FOUND"]
   [:div {:class "NotFound__description"}
    [:span "The page you’re looking for does not exist."]
    [:span "Please check the URL and try again."]]])
