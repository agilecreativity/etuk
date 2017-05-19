(ns etuk.examples.download-selenium
  (:gen-class)
  (:require [etuk.browser-utils :as but :refer [start-chrome-session]]
            [etuk.core-navigator :as cnv :refer :all]
            [etuk.core-wait :as cwt :refer :all]
            [webica.expected-conditions :as ec]
            [webica.remote-web-driver :as browser]
            [webica.web-element :as element]))

;; Download jars file from Selenium
(defn download-selenium-jars
  []
  (start-chrome-session)
  (browser/get "http://www.seleniumhq.org/")

  ;; Click the big Download link
  (cnv/navigate :wdriver (cwt/get-instance)
                :wfn      ec/presence-of-element-located
                :type     :css-selector
                :expr     "#sidebar > div.downloadBox > a"
                :act-name element/click
                :act-arg  nil)

  ;; Then click on the download Selenium Java driver 3.3.0 version link
  (cnv/navigate :wdriver (cwt/get-instance)
                :wfn  ec/presence-of-element-located
                :type :css-selector
                :expr "#mainContent > table:nth-child(13) > tbody > tr:nth-child(1) > td:nth-child(4) > a"
                :act-name element/click
                :act-arg  nil))

(defn -main [& args]
  (try
    (download-selenium-jars)
    (browser/quit)
    (println "Done!")
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
