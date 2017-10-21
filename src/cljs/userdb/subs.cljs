(ns userdb.subs
  (:require-macros [reagent.ratom :refer [reaction]])
  (:require [re-frame.core :as rf]))




;;
;; Domino 4 - Subscription handlers
;; These are run to calculate the view data after a state change.
;; Data returned by the query functions is used in the view functions
;;

;; Register subscription for
;;
;; NOTE: ids
(rf/reg-sub
 :name
 (fn [db [_]]
   (:name db)))

(rf/reg-sub
  :add-user
  (fn [db [_ user]]
    (get-in db [:users (-> user :id keyword)])))


;; Register subscription for
(rf/reg-sub
  :update-user
  (fn [db [_ user]]
    (get-in db [:users (-> user :id keyword)])))


;; Register subscription for user list
;; TODO: - how do we limit the size of the user list
(rf/reg-sub
  :list-users
  (fn [db [_]]
    (-> db :users vals)))


;; Register subscription for
;; :select-user
(rf/reg-sub
  :select-user
  (fn [db [_ user-id]]
    (get-in db [:users user-id])))
