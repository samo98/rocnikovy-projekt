(ns rocnikovy-projekt.modals
  (:require [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.state :refer [app-state]]
            [rocnikovy-projekt.helpers :refer [generate-id]]))

;; -------------------------
;; Cursors

(def modals-cursor
  (reagent/cursor app-state [:modals]))

(defn close-modal [id]
  (swap! modals-cursor #(filter (fn [element] (not= (:id element) id)) %)))

(defn open-modal [component props resolveFn]
  (let [id (generate-id)]
    (swap! modals-cursor #(conj % {:id id :component component :props props
                                   :resolveFn (fn [] (close-modal id)(resolveFn))}))))

(defn notice-dialog [{{header :header text :text} :props resolveFn :resolveFn}]
  [ui/dialog {:open true :modal true}
    [:div {:class "NoticeDialog"}
      [:div {:class "NoticeDialog__title"} header]
      [:div {:class "NoticeDialog__description"} text]
      [:div
        [ui/flat-button {:primary true
                         :label "OK"
                         :on-click resolveFn}]]]])

(defn modals-renderer []
  [:div (map (fn [{id :id props :props resolveFn :resolveFn component :component}]
              [component {:key id :props props :resolveFn resolveFn}])
          @modals-cursor)])
