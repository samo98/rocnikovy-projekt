(ns rocnikovy-projekt.loading
  (:require [cljs-react-material-ui.reagent :as ui]))

(defn loading-helper [{:keys [is-loaded placeholder]} & children]
  (if (= is-loaded true)
    (map-indexed #(with-meta %2 {:key %1}) children)
    [:div.LoadingHelper
      (if (nil? placeholder)
        [ui/refresh-indicator
          {:top 0
           :left 0
           :size 64
           :loading-color "#00c371"
           :status "loading"
           :style {:display "inline-block"
                   :position "relative"
                   :font-size "1rem"}}]
        [:span {:style {:margin-top "2rem"}} placeholder])]))
