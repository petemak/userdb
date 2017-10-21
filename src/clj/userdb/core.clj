(ns userdb.core)


;; |'start            |""              |Buttons disabled  |:initialize-db    'ready     |
;; |'ready            |""              |Add enabled       |:add-user         'user-added|
;; |'name-reguired    |"Enter name"    |Add disabled      |:add-user         'user-added|
;; |'age-reguired     |"Enter age "    |Add disabled      |:add-user         'user-added|
;; |'age-reguired     |"Enter age "    |Add disabled      |:add-user         'user-added|
;;
(def fsm {'start {:initialize-db 'ready}
          'ready {:add-user      'adding-user
                  :remove-user   'removing-user}
          'adding-user {:add-user-success 'ready
                        :add-user-failed  'error-add-user}
          'removing-user {:remove-user-success 'ready
                          :remove-user-failws 'error-remove-user}})