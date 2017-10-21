(ns userdb.db)

(def default-db
  {:name "user-db"
   :selected-user :nil
   :users {:user-00001 {:id "user-00001"
                        :first-name "Vivs"
                        :last-name "Mak"
                        :name "Vivs Vivi"
                        :role "Daughter"
                        :age 6
                        :address {:street "Flyboat House"
                                  :number "55"
                                  :code "LS10 1JJ"
                                  :city "Leeds"}}
           :user-00002 {:id "user-00002"
                        :first-name "Peter"
                        :last-name "Mak"
                        :role "Father"
                        :age 38
                        :address {:street "Zugerstrasse"
                                  :number "24a"
                                  :code "8810"
                                  :city "Horgen"}}
           :user-00003 {:id "user-00003"
                        :first-name "Duffy"
                        :last-name "Duck"
                        :role "Friend"
                        :age 12
                        :address {:street "Duffy Street"
                                  :number "1"
                                  :code "8050"
                                  :city "Zurich"}}
           :user-00004 {:id "user-00004"
                        :first-name "Wonder"
                        :last-name "Woman"
                        :role "Super Hero"
                        :age 27
                        :address {:street "Duffy Street"
                                  :number "1"
                                  :code "x221z5"
                                  :city "Themyscira"}}
           :user-00005 {:id "user-00005"
                        :first-name "Pallas"
                        :last-name "Athena"
                        :role "Goddess of Wisdom"
                        :age 30
                        :address {:street "Mount Olympus"
                                  :number "XIX"
                                  :code "Ath123"
                                  :city "Athen"}}
           :user-00009 {:id "user-00009"
                        :first-name "Spider"
                        :last-name "Man"
                        :role "Hero"
                        :age 32
                        :address {:street "Ingram Street"
                                  :number "20"
                                  :code "8050"
                                  :city "New York"}}
           :user-00010 {:id "user-00010"
                        :first-name "Bat"
                        :last-name "Man"
                        :role "Hero"
                        :age 34
                        :address {:street "Mountain Drive, Gotham"
                                  :number "1007"
                                  :code "1233"
                                  :city "New York"}}}})