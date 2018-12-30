(ns micro-graph.graph-test
  (:require [clojure.test :refer :all]
            [micro-graph.graph :refer :all]))

(def g {:frankfurt {:mannheim 85 :wurzburg 217 :kassel 173}
        :mannheim {:frankfurt 85 :karlsruhe 80}
        :karlsruhe {:augsburg 250 :mannheim 80}
        :augsburg {:karlsruhe 250 :munchen 84}
        :wurzburg {:erfurt 186 :numberg 103 :frankfurt 217}
        :erfurt {:wurzburg 186}
        :numberg {:wurzburg 103 :stuttgart 183 :munchen 167}
        :munchen {:numberg 167 :augsburg 84 :kassel 502}
        :kassel {:frankfurt 173 :munchen 502}
        :stuttgart {:numberg 183}})

(deftest bfs-test
  (testing "breadth first search test"
    (is
     (= 
      (graph-bfs g :frankfurt)
      [:frankfurt :mannheim :wurzburg :kassel :karlsruhe :erfurt :numberg :munchen :augsburg :stuttgart]))))


