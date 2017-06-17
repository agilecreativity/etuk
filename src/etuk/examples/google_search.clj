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

(defn- add-space-if-none-exist
  "Utils function to add whitespaces to the end of the text
  to make send-keys work as expected."
  [text]
  (if-not (re-find #"(?:\s+)" text)
    (str text " ")
    text))

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
                    :act-arg  (add-space-if-none-exist terms))

      (cnv/navigate :wdriver  wdriver
                    :wfn      ec/presence-of-element-located
                    :type     :name
                    :expr     "btnG"
                    :act-name element/click
                    :act-arg  nil))))

(defn -main
  [& args]
  (try
    ;; NOTE: send-keys "Clojure" not working but - wrong type of argument for send-keys?
    ;;       send-keys "Clojure Trending" is working
    ;; Tips: workaround is to always append one space to the text if we don't have one
    (if args
      (google-search (first args))
      (google-search "Trending Clojure"))
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
