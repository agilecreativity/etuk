(ns etuk.core
  (:require [etuk.browser-utils :refer [start-chrome-session start-firefox-session] :as but]
            [etuk.core-wait :refer :all :as cwt]
            [etuk.core-navigator :refer :all :as cnv]
            [webica.remote-web-driver :as browser]
            [webica.expected-conditions :as ec]
            [webica.web-element :as element])
  (:gen-class))

(defn download-selenium-jars
  []
  (start-chrome-session)
  (browser/get "http://www.seleniumhq.org/")

  ;; Click the big Download link
  (cnv/navigate {:wdriver  (cwt/get-instance)
                 :wfn      ec/presence-of-element-located
                 :type     :css-selector
                 :expr     "#sidebar > div.downloadBox > a"
                 :act-name element/click
                 :act-arg  nil})

  ;; Then click on the download Selenium Java driver 3.3.0 version link
  (cnv/navigate {:wdriver  (cwt/get-instance)
                 :wfn      ec/presence-of-element-located
                 :type     :css-selector
                 :expr     "#mainContent > table:nth-child(13) > tbody > tr:nth-child(1) > td:nth-child(4) > a"
                 :act-name element/click
                 :act-arg  nil}))

(defn -main [& args]
  (try
    (download-selenium-jars)
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
