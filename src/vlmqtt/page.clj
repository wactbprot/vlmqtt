(ns vlmqtt.page
  ^{:author "Thomas Bock <wactbprot@gmail.com>"
    :doc "Simple replication state overview page delivered by server.clj."}
  (:require [hiccup.form :as hf]
            [hiccup.page :as hp]
            [clojure.string :as string]))

(defn not-found []
  (hp/html5
   [:h1 "404 Error!"]
   [:b "Page not found!"]
   [:p [:a {:href ".."} "Return to main page"]]))

;;........................................................................
;; nav
;;........................................................................
(defn nav [conf]
  [:div.uk-navbar-container
   {:uk-navbar ""}
   [:div.uk-navbar-center
    [:ul.uk-navbar-nav
      [:li [:a {:target "_blank"
                :href "https://gitlab1.ptb.de/vaclab/repliclj"} "gitlab"]]
      [:li [:a {:target "_blank"
                :href "http://a75438:5601/app/discover#/view/6fde0090-06ff-11ec-a0ed-9fa5b8b37aed"} "elasticsearch"]]
     [:li [:a { :target "_blank"
                :href "https://docs.couchdb.org/en/main/replication/index.html"} "repli docu"]]]]])

;;........................................................................
;; body
;;........................................................................
(defn body [conf content libs]
  (into [:body#body
         (nav conf)
         [:div.uk-container.uk-padding.uk-margin
          [:article.uk-article
           [:h4.uk-article-title.uk-text-uppercase.uk-heading-line.uk-text-center.uk-text-muted
            [:a.uk-link-reset {:href ""} " what have you"]]
           [:p.uk-article-meta "date"]
           [:p.uk-text-lead
            content]]]]
        libs))

;;........................................................................
;; head
;;........................................................................
(defn head [conf]
  [:head [:title "repliclj"]
   [:meta {:charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   (hp/include-css "/css/uikit.css")])

(defn slider [no]
  [:p
   [:h3 (str "valve " no)] 
   [:input.uk-range.uk-form-success
    {:id (str "vacuum_se3_valve_" no "_position")
     :type "range"
     :min "0"
     :max "20000"
     :step "100"}]])

;;........................................................................
;; index
;;........................................................................
(defn index [conf]
  (hp/html5
   (head conf)
   (body conf
         [:div
          (slider 1)
          (slider 2)
          (slider 3)
          (slider 4)
          (slider 5)
          (slider 6)        
          ]
         [(hp/include-js "/js/jquery.js")
          (hp/include-js "/js/uikit.js")
          (hp/include-js "/js/uikit-icons.js")
          (hp/include-js "/js/ws.js")])))
