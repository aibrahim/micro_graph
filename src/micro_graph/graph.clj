(ns micro-graph.graph)

(defprotocol IGraph
  (graph-vertices [this])
  (graph-edges [this])
  (graph-add-vertex [this vertex])
  (graph-add-edge [this edge])
  (graph-adj-mat [this]))


(extend-type clojure.lang.IPersistentMap
  IGraph
  (graph-vertices [this] (-> this keys vec))
  (graph-edges [this] (->> this
                           (mapcat
                            (fn [[src v]]
                              (mapv 
                               (fn [[dst w]]
                                 [src dst w]) v)))
                           (into #{})
                           vec))
  (graph-add-vertex [this vertex]
    (assoc this vertex {}))
  (graph-add-edge [this [src dist w]]
    (assoc-in this [src dist] w)))

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


