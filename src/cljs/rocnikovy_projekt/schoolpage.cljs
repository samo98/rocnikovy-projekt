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
    (let [schoolData @(school-cursor id)]
      [:div {:class "School"}
        (loading-helper {:is-loaded (some? schoolData)}
          [:div {:class "School__title"} (:name schoolData)]
          [back-arrow "/dashboard"]
          [:div {:class "School__body"}
            [:div {:class "School__body__item"}
              [:div {:class "School__body__item__title"} "Webstránka"]
              [:a {:href (:webpage_url schoolData) :target "_blank"}
                (:webpage_url schoolData)]]
            [:div {:class "School__body__item"}
              [:div {:class "School__body__item__title"} "Adresa"]
              [:div (:address schoolData)]]
            [:div {:class "School__body__item"}
              [:div {:class "School__body__item__title"} "Skratka"]
              [:div (:acronym schoolData)]]])])))
