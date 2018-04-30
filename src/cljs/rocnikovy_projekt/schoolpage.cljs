(ns rocnikovy-projekt.schoolpage
  (:require [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]
            [rocnikovy-projekt.helpers :refer [back-arrow]]
            [rocnikovy-projekt.loading :refer [loading-helper]]
            [rocnikovy-projekt.api :refer [make-remote-call]]))


(defn school-cursor [id]
  (reagent/cursor app-state [:schools (keyword id)]))

(defn fetch-school [id]
  (make-remote-call (str "/schools/" id)
    (fn [response]
      (reset! (school-cursor id) response))))

(defn school-page [{id :id}]
  (fetch-school id)
  (fn [{id :id}]
    [:div {:class "School"}
      (loading-helper {:is-loaded (some? @(school-cursor id))}
        [:div {:class "School__title"} (:name @(school-cursor id))]
        [back-arrow "/dashboard"])]))
