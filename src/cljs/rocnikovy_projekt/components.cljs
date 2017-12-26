(ns rocnikovy-projekt.components
  (:require [cljs-react-material-ui.icons :as ic]
            [cljs-react-material-ui.reagent :as ui]
            [reagent.core :as reagent]
            [rocnikovy-projekt.cursors :refer [schools-cursor dashboard-search-cursor filtered-school-ids]]
            [rocnikovy-projekt.helpers :refer [get-school-by-id]]))

;; -------------------------
;; Helpers

(defn material-filter [] (aget js/MaterialUI "AutoComplete" "caseInsensitiveFilter"))

;; -------------------------
;; Views

(defn home-page []
  [:div {:class "Homepage"}
   [:div {:class "Homepage__title"} "Vitajte na stranke mojho rocnikoveho projektu"]
   [:div {:class "Homepage__link"} [:a {:href "/dashboard"} "Zoznam škôl"]]])

(defn dashboard-row [id]
  [ui/table-row {:class-name "DashboardRow" :hoverable true}
    [ui/table-row-column (:name (get-school-by-id id))]])

(defn dashboard-page []
  [:div {:class "Dashboard"}
   [:div {:class "Dashboard__title"} "Zoznam škôl"]
   [:a {:href "/" :style {:text-decoration "none"}}
    [:div {:class "Dashboard__back"} (ic/navigation-arrow-back)
     [:div {:style {:margin "auto 0" :color "black"}} "Späť"]]]
   [:div {:class "Dashboard__search"}
    [ic/action-search {:class-name "Dashboard__search__icon"}]
    [ui/auto-complete {:dataSource (map (fn [id] (:name (get-school-by-id id))) (keys @schools-cursor))
                       :name "Name filter"
                       :search-text (:text @dashboard-search-cursor)
                       :filter (material-filter)
                       :on-update-input (fn [text]
                                          (reset! (reagent/cursor dashboard-search-cursor [:text]) text)
                                          (reset! (reagent/cursor dashboard-search-cursor [:searchIds]) 
                                            (filter (fn [id] ((material-filter) text (:name (get @schools-cursor id))))
                                            (keys @schools-cursor))))}]
    [ic/navigation-cancel {:class-name "Dashboard__search__cancel" :on-click (fn [] (reset! dashboard-search-cursor {:text ""}))}]]
   [ui/table {:class-name "Dashboard__table" :selectable false}
    [ui/table-header {:adjust-for-checkbox false :display-select-all false}
      [ui/table-row
        [ui/table-header-column "Názov"]]]
    [ui/table-body
      (for [id (filtered-school-ids)]
        ^{:key id} [dashboard-row id])]]])
