(ns userdb.views
  (:require [re-frame.core :as rf]
            [re-frame-datatable.core :as dt]
            [re-frame-datatable.views :as dv]))




(def header-names
  ["Id" "Name" "Age"])

;;Domino 5 - view functions
(defn header
  "The main application header"
  []
  [:div.sixteen.wide.column
   [:h1.ui.blue.header
     "User Database"]])

(defn user-list
  "Returns the user list in tabular form"
  []
  [:div  {:id "main"}
   [:h3 "User List"]
   [:table {:class "table table-striped table-hover"}
     (into [:thead]
       (for [name header-names]
         [:th name]))

     (into [:tbody]
       (for [user @(rf/subscribe [:list-users])]
         [:tr {:class "active"}
           [:td (:id user)] [:td (:name user)] [:td (:age user)]]))]])


(defn pagination-controls
  []
  (let [db-key :users
        sub-ids [:list-users]
        pagination-state (rf/subscribe [::dt/pagination-state db-key sub-ids])]
    (fn []
      (let [{:keys [::dt/cur-page ::dt/pages]} @pagination-state
            total-pages (count pages)
            next-enabled? (< cur-page (dec total-pages))
            prev-enabled? (pos? cur-page)]
          [:div
            [:div {:style {:display "inline-block"
                           :margin ".5em"}}
              (str "Page: " cur-page " - ")
              [:span "users: "]
              [:strong (inc (second (last pages)))]]
            [:div.ui.pagination.mini.menu
              [:a.item
                {:on-click #(when prev-enabled?
                              (rf/dispatch [::dt/select-prev-page db-key @pagination-state]))
                 :class (when-not prev-enabled? "disabled")}
                [:i.left.chevron.icon]]
              [:a.item
                {:on-click #(when next-enabled?
                              (rf/dispatch [::dt/select-next-page db-key @pagination-state]))
                 :class (when-not next-enabled? "disabled")}
                [:i.right.chevron.icon]]]]))))


(defn user-list-dt []
  [:div.ui.grid
    [:div.row
      [:div.twelve.wide.column [:p]]
      [:div.right.floated.four.wide.column
        [pagination-controls]]]
   [:div.row
     [:div.sixteen.wide.column
      [dt/datatable
       :users
       [:list-users]
       [{::dt/column-key [:id]
         ::dt/column-label "Id"}
        {::dt/column-key [:first-name]
         ::dt/column-label "First Name"}
        {::dt/column-key [:last-name]
         ::dt/column-label "Last Name"}
        {::dt/column-key [:role]
         ::dt/column-label "Role"}
        {::dt/column-key [:age]
         ::dt/column-label "Age"}]
       {::dt/pagination {::dt/enabled? true
                         ::dt/per-page 5}
        ::dt/selection {::dt/enabled? true}
        ::dt/table-classes ["ui" "celled" "table"]}]]]])

(defn user-editor
  "The user editor component form"
  []
  (let [selected-users @(rf/subscribe [::dt/selected-items
                                       :users
                                       :user-list])
        selected-user (if (nil? selected-users) {} (last selected-users))]
    [:form.ui.form
     [:h4.ui.diving.header "User Details"]
     [:div.field
       [:div.two.fields
         [:div.field
           [:label "First Name"]
           [:input {:type "text" :name "firstname" :placeholder "First name"} (:first-name selected-user)]]
         [:div.field
           [:label "Last Name"]
           [:input {:type "text" :name "lastname" :placeholder "Last name"} (:last-name selected-user)]]]]
     [:div.field
       [:div.two.fields
         [:div.field
           [:label "Gender"]
           [:select.ui.fluid.dropdown
             [:option {:value "M"} "Male"]
             [:option {:value "F"} "Female"]]]
         [:div.field
           [:label "Age"]
           [:input {:type "text" :name "age" :placeholder "Age"} (:age selected-user)]]]]
     [:div.field
       [:label "Family Role and Relationship"]
       [:select.ui.fluid.dropdown
         [:option {:value "Father"} "Father"]
         [:option {:value "Mother"} "Mother"]
         [:option {:value "Daughter"} "Daughter"]
         [:option {:value "Son"} "Son"]]]
     [:h4.ui.diving.header "Address"]
     [:div.fields
       [:div.twelve.wide.field
         [:label "Street Name"]
         [:input {:type "text" :name "street" :placeholder "Street name"} (get-in selected-user [:address :street])]]
       [:div.four.wide.field
         [:label "Number"]
         [:input {:type "text" :name "streetnumber" :placeholder "Street number"}]]]
     [:div.fields
       [:div.four.wide.field
         [:label "Code"]
         [:input {:type "text" :name "postcode" :placeholder "Postcode"}]]
       [:div.twelve.wide.field
         [:label "City"]
         [:input {:type "text" :name "City" :placeholder "City"}]]]
     [:div.field
       [:label "Country"]
       [:div.ui.fluid.search.selection.dropdown
         [:input {:type "hidden" :name "country"}]
         [:i.dropdown.icon]
         [:div.default.text "Select country"]
         [:div.menu
           [:div.item {:data-value "at"}
             [:i.at.flag  "Austria"]]
           [:div.item {:data-value "be"}
             [:i.be.flag  "Belgium"]]
           [:div.item {:data-value "dk"}
             [:i.dk.flag  "Denmark"]]
           [:div.item {:data-value "gb"}
             [:i.gb.flag  "England"]]
           [:div.item {:data-value "et"}
             [:i.et.flag  "Ethiopia"]]
           [:div.item {:data-value "de"}
             [:i.de.flag  "Germany"]]
           [:div.item {:data-value "ch"}
             [:i.ch.flag  "Switzerland"]]]]]
     [:div.ui.button {:tabindex "0"} "Add User"]]))

(defn user-editor2
  "The user editor component form"
  []
  [:form.ui.form
   [:h4.ui.diving.header "User Details"]
   (let [selected-users @(rf/subscribe [::dt/selected-items
                                        :users
                                        [:list-users]])
         selected-user (if (nil? selected-users) {} (last selected-users))]
     [:code selected-users]
     [:div.field
      [:div.two.fields
       [:div.field
        [:label "First Name"]
        [:input {:type "text" :name "firstname" :placeholder "First name"} (:first-name selected-user)]]
       [:div.field
        [:label "Last Name"]
        [:input {:type "text" :name "lastname" :placeholder "Last name"} (:last-name selected-user)]]]]
     [:div.field
      [:div.two.fields
       [:div.field
        [:label "Gender"]
        [:select.ui.fluid.dropdown
         [:option {:value "M"} "Male"]
         [:option {:value "F"} "Female"]]]
       [:div.field
        [:label "Age"]
        [:input {:type "text" :name "age" :placeholder "Age"} (:age selected-user)]]]]
     [:div.field
      [:label "Family Role and Relationship"]
      [:select.ui.fluid.dropdown
       [:option {:value "Father"} "Father"]
       [:option {:value "Mother"} "Mother"]
       [:option {:value "Daughter"} "Daughter"]
       [:option {:value "Son"} "Son"]]]
     [:h4.ui.diving.header "Address"]
     [:div.fields
      [:div.twelve.wide.field
       [:label "Street Name"]
       [:input {:type "text" :name "street" :placeholder "Street name"} (get-in selected-user [:address :street])]]
      [:div.four.wide.field
       [:label "Number"]
       [:input {:type "text" :name "streetnumber" :placeholder "Street number"}]]]
     [:div.fields
      [:div.four.wide.field
       [:label "Code"]
       [:input {:type "text" :name "postcode" :placeholder "Postcode"}]]
      [:div.twelve.wide.field
       [:label "City"]
       [:input {:type "text" :name "City" :placeholder "City"}]]]
     [:div.field
      [:label "Country"]
      [:div.ui.fluid.search.selection.dropdown
       [:input {:type "hidden" :name "country"}]
       [:i.dropdown.icon]
       [:div.default.text "Select country"]
       [:div.menu
        [:div.item {:data-value "at"}
         [:i.at.flag  "Austria"]]
        [:div.item {:data-value "be"}
         [:i.be.flag  "Belgium"]]
        [:div.item {:data-value "dk"}
         [:i.dk.flag  "Denmark"]]
        [:div.item {:data-value "gb"}
         [:i.gb.flag  "England"]]
        [:div.item {:data-value "et"}
         [:i.et.flag  "Ethiopia"]]
        [:div.item {:data-value "de"}
         [:i.de.flag  "Germany"]]
        [:div.item {:data-value "ch"}
         [:i.ch.flag  "Switzerland"]]]]]
     [:div.ui.button {:tabindex "0"} "Add User"])])


(defn selection-panel
  []
  [:div.ui.blue.message
    [:i.close.icon]
    [:div.header
      "Selected item:"]
    [:hr]
    [:code
      @(rf/subscribe [::dt/selected-items
                      :users
                      [:list-users]])]])

(defn footer
  "The footer contains informational links"
  []
  [:div {:id "footer"}
   "No | Content | Yet "])



;;(defn main-panel
;;  []
;;  [:div {:id "container"}
;;   [:p [:div "Hello"]]
;;   [header]
;;   [user-list]
;;   [user-editor]
;;   [footer]])

(defn main-panel
  []
  [:div.ui.internally.celled.grid
   [:div.sixteen.wide.column
     [header]]
   [:div.sixteen.wide.column
     [user-list-dt]]
   [:div.eight.wide.column
    [selection-panel]]
   [:div.eight.wide.column
    [user-editor2]]
   [:div.sixteen.wide.column
     [footer]]])


