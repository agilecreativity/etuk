(ns etuk.examples.google-search
  (:require [easy-config.core :as ecf]
            [etuk.browser-utils :as but :refer [start-chrome-session]]
            [etuk.core-navigator :as cnv :refer :all]
            [etuk.core-wait :as cwt :refer :all]
            [webica.expected-conditions :as ec]
            [webica.remote-web-driver :as browser]
            [webica.web-element :as element]))

(defn- starter-page
  "Navigate to the starter page"
  [url]
  (start-chrome-session)
  (browser/get url))

(defn google-search
  [terms]
  (let [url "https://www.google.com"]
    (starter-page url)
    (let [wdriver (cwt/get-instance)]
      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :name
                    :expr     "q"
                    :act-name element/send-keys
                    :act-arg  terms)

      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :name
                    :expr     "btnG"
                    :act-name element/click
                    :act-arg  nil))))

(defn -main
  [& args]
  (try
    (google-search "Clojure Github Trending")
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
