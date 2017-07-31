(ns etuk.browser-utils
  (:require [webica.core :as w])
  (:require [webica
             [chrome-driver :as chrome]
             [firefox-driver :as firefox]]))

(defn ^:private is-windows?
  "Check for the system type and return true if it is Windows based system."
  []
  (re-find #"windows" (clojure.string/lower-case (System/getProperty "os.name"))))

(def ^:private default-chrome-driver
  "Default location for Chrome web driver."
  (str (System/getenv "HOME") (if (is-windows?) "/apps/chromedriver.exe" "/apps/chromedriver")))

(def ^:private default-firefox-driver
  "Default location for Firefox web driver. Note tested with Firefox 52.0.2"
  (str (System/getenv "HOME") (if (is-windows?) "/apps/geckodriver.exe" "/apps/geckodriver")))

(defn ^:private set-chrome-driver-path
  "Set the correct property for Google Chrome to work properly."
  ([]
   set-chrome-driver-path default-chrome-driver)
  ([driver-path]
   (let [driver-prop "webdriver.chrome.driver"]
     (System/setProperty driver-prop driver-path)
     ;; Return the driver path
     driver-path)))

(defn ^:private set-gecko-driver-path
  "Set the correct property for Firefox/Gecko to work properly."
  ([]
   set-gecko-driver-path default-firefox-driver)
  ([driver-path]
   ;; Note: may be we need to use just one of this?
   (System/setProperty "webdriver.firefox.driver" driver-path)
   (System/setProperty "webdriver.gecko.driver" driver-path)
   driver-path))

;; Public APIs

(defn start-chrome-session
  "Start automation session using Google Chrome"
  ([]
   (start-chrome-session (set-chrome-driver-path)))
  ([driver-path]
   (chrome/start-chrome driver-path)))

(defn start-firefox-session
  "Start automation session using Firefox"
  []
  ;; TODO: adjust this to make it similar to the start-chrome-session
  (set-gecko-driver-path)
  (firefox/start-firefox))
