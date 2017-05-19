(ns etuk.examples.google-search
  (:gen-class)
  (:require [etuk.browser-utils :as but :refer [start-chrome-session]]
            [webica.remote-web-driver :as browser]
            [webica.web-driver :as driver]))

(defn google-search [driver]
  (start-chrome-session)
  (browser/get "https://www.google.com/")
  (let [driver (driver/get-instance)]
    (let [element1 (.findElement driver (org.openqa.selenium.By/name "q"))]
      (.sendKeys element1 (into-array ["Clojure Trending"]))
      (.click (.findElement driver (org.openqa.selenium.By/name "btnG"))))))

(defn -main [& args]
  (try
    (let [driver (driver/get-instance)]
      (do
        (google-search driver)))
    (println "Done!")
    (catch Exception e
      (.printStackTrace e)
      (println (str "Unexpected errors: " (.getMessage e))))))
