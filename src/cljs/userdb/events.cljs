(ns userdb.events
  (:require [re-frame.core :as rf]
            [userdb.db :as db]))


;; exponential functions
(defn pow
  [x y]
  (loop [res 1
         exp y]
    (if (= exp 0)
      res
      (recur (* res x) (dec exp)))))

;; generate a rundom id
(defn gen-id
  []
  (loop [id 0
          c 0]
     (if (= c 4)
       id
       (recur (+ (*  (pow 10 c)  (rand-int 9)) id)
              (inc c)))))

;; Add the prefix
;; to create and ID
(defn make-user-id
  [id]
  (str "user-" (id)))

;; Register handler for handling :add-user
;; Add-user is dispached from the add-user view
;;
;; SIDE EFFECT!! - should actually create an interceptor
;;                which generates the id and palces it
;;                in the coeffects
;; with the interceptor in place we can access the generated
;; unique use id like this
;; (assoc db [:users (keyword(:gen-user-id coeffs)] (assoc user :id (:gen-user-id coeffs))
;;
;;
;; (rf/reg-event-db
;; :add-user
;; (fn [db [_ user]
;; (let [user-id (make-user-id (gen-id))]
;;          user-kw (keyword (user-id))
;;      assoc-in db [:users user-kw] user))

(def gen-user-id
  (rf/->interceptor
    :id :gen-user-id
    :before (fn [context]
              (let [user-id (-> gen-id make-user-id)]
                (assoc-in context [:coeffects :event 1 :id] user-id)))))

;; Domino 1 - Events
;; 1) :intialise
;; 2) :add-user
;; 3) :update-user
;; 4) :remove-user

;;
;; Domino 2 - Event handlers

;; 1: Register handler for initialising database
;; Simply returns the default database
(rf/reg-event-db
  :initialize-db
  (fn  [_ _]
    db/default-db))
;;
;; 2. Register handler for adding a new user
;; Uses the gen-user-id interceptor to generate and place
;; an id into the user object. We will use that ID to create
;; a key for registering the user object in the app-db
(rf/reg-event-db
  :add-user
  [gen-user-id]
  (fn [db [_ user]]
    (let [user-kw (-> user :id keyword)]
      (assoc-in db [:users user-kw] user))))


;; 3. Register handler for updating a user.
;; The user object must contain modifed user data
;; and is merged with the original user data in the
;; db.
(rf/reg-event-db
  :update-user
  (fn [db [_ user]]
    (update-in db [:users (:id user)] merge user)))


;; 4. Register handler for removing a user
;; The user map expected to contain a user key
(rf/reg-event-db
  :remove-user
  (fn [db [_ user-id]]
    (update-in db [:users] dissoc user-id)))


;; 5. Register handler for slecting a user
(rf/reg-event-db
  :select-user
  (fn [db [_ user-id]]
    (assoc db :selected-user user-id)))






