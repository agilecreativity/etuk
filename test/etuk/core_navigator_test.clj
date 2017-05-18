(ns etuk.core-navigator-test
  (:require [etuk.core :as sut]
            [etuk.core-navigator :refer :all :as cnv]
            [clojure.test :refer :all :as t]))

(t/testing "::element-type parameter"
  (is (= true (s/valid? ::element-type :id)))
  (is (= false (s/valid? ::element-type :not-id))))

(t/testing "find-element-by"
  (t/testing "Invalid argument"
    (is (thrown? Exception
                 (cnv/find-element-by :invalid-key "dont-care")))
