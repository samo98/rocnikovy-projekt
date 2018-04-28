(ns rocnikovy-projekt.helpers
  (:require [cljs-react-material-ui.icons :as ic]))

(defn back-arrow [url]
  [:a {:href url :style {:text-decoration "none"}}
    [:div {:class "BackArrow"} (ic/navigation-arrow-back)
      [:div {:style {:margin "auto 0" :color "black"}} "Späť"]]])
