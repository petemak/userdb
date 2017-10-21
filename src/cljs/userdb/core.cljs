(ns userdb.core
  (:require [reagent.core :as reagent]
            [re-frame.core :as rf]
            [userdb.events]
            [userdb.subs]
            [userdb.views :as views]
            [userdb.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

;;
;; Specified in project.clj as start function
;; In turn calls the views.main-panel functions
(defn mount-root []
  (rf/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export run []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
