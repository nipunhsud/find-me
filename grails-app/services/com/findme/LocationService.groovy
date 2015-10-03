package com.findme

import java.nio.file.Path
import java.nio.file.Paths

import org.apache.lucene.analysis.*
import org.apache.lucene.analysis.standard.StandardAnalyzer
import org.apache.lucene.document.Document
import org.apache.lucene.document.Field
import org.apache.lucene.document.FieldType
import org.apache.lucene.document.IntField
import org.apache.lucene.document.StoredField
import org.apache.lucene.document.Field.Store
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.index.IndexReader
import org.apache.lucene.index.IndexWriter
import org.apache.lucene.index.IndexWriterConfig
import org.apache.lucene.index.IndexableField
import org.apache.lucene.queries.function.ValueSource
import org.apache.lucene.search.Filter
import org.apache.lucene.search.IndexSearcher
import org.apache.lucene.search.MatchAllDocsQuery
import org.apache.lucene.search.ScoreDoc
import org.apache.lucene.search.Sort
import org.apache.lucene.search.TopDocs
import org.apache.lucene.spatial.SpatialStrategy
import org.apache.lucene.spatial.prefix.RecursivePrefixTreeStrategy
import org.apache.lucene.spatial.prefix.tree.GeohashPrefixTree
import org.apache.lucene.spatial.prefix.tree.SpatialPrefixTree
import org.apache.lucene.spatial.query.SpatialArgs
import org.apache.lucene.spatial.query.SpatialOperation
import org.apache.lucene.store.Directory
import org.apache.lucene.store.SimpleFSDirectory

import com.findme.inbound.Question;
import com.spatial4j.core.context.SpatialContext
import com.spatial4j.core.distance.DistanceUtils
import com.spatial4j.core.shape.Point
import com.spatial4j.core.shape.Shape


class LocationService {
	
	private IndexWriter indexWriter;
	private IndexReader indexReader;
	private IndexSearcher searcher;
	private SpatialContext ctx;
	private SpatialStrategy strategy;
	
	def getUsersAround(lat, lng,dist){
		
		String indexPath = "/Users/nipunsud/Documents/lucene_practices/geo_spatial_index";
		log.info "Index path "+ indexPath
		
		SpatialSearch(indexPath)
		//Indexes sample documents
		//indexDocuments();
		setSearchIndexPath(indexPath);
		
		//Get Places Within 4 kilometers from cubbon park.
		search(12.9558,77.791995,10);
	}
	
	public SpatialSearch(String indexPath) {
	 
	 StandardAnalyzer analyzer = new StandardAnalyzer();
	 IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
	 Directory directory;
	 
	 try {
		 Path path = Paths.get(indexPath)
		 log.info "PAth clas "+ path.getClass().name
	  directory = new SimpleFSDirectory(path);
	  log.info "Directory "+ directory
	  indexWriter = new IndexWriter(directory, iwc);
	 } catch (IOException e) {
	  // TODO Auto-generated catch block
	  e.printStackTrace();
	 }
	 
	 this.ctx = SpatialContext.GEO;
	 log.info "CTX "+ ctx
	 
	 SpatialPrefixTree grid = new GeohashPrefixTree(ctx, 11);
	 this.strategy = new RecursivePrefixTreeStrategy(grid, "location");
	}
	
	public void indexDocuments() throws IOException {
		
		def users = User.findAll()
		users.each{
			indexWriter.addDocument(newGeoDocument(it.userId, it.username, ctx.makePoint(it.currentLatitude, it.currentLongitude)));
		}
	
		
	 
	 
	 /*indexWriter.addDocument(newGeoDocument(1, "Bangalore", ctx.makePoint(12.9558, 77.620979)));
	 indexWriter.addDocument(newGeoDocument(2, "Cubbon Park", ctx.makePoint(12.974045, 77.591995)));
	 indexWriter.addDocument(newGeoDocument(3, "Tipu palace", ctx.makePoint(12.959365, 77.573792)));
	 indexWriter.addDocument(newGeoDocument(4, "Bangalore palace", ctx.makePoint(12.998095, 77.592041)));
	 indexWriter.addDocument(newGeoDocument(5, "Monkey Bar", ctx.makePoint(12.97018, 77.61219)));
	 indexWriter.addDocument(newGeoDocument(6, "Cafe Cofee day", ctx.makePoint(12.992189, 80.2348618)));
	 indexWriter.addDocument(newGeoDocument(7, "Test", ctx.makePoint(12.9558, 77.902995)));*/
	 
	 indexWriter.commit();
	 indexWriter.close();
	}
	
   
	private Document newGeoDocument(int id, String name, Shape shape) {
   
	 FieldType ft = new FieldType();
	// ft.setIndexed(true);
	 ft.setStored(true);
   
	 Document doc = new Document();
	 
	 doc.add(new IntField("userId", id, Store.YES));
	 doc.add(new Field("userName", name, ft));
	 for(IndexableField f:strategy.createIndexableFields(shape)) {
	  doc.add(f);
	 }
	 
	 doc.add(new StoredField(strategy.getFieldName(), ctx.toString(shape)));
	 
	 return doc;
	}
	
	public void setSearchIndexPath(String indexPath) throws IOException{
	Path path = Paths.get(indexPath)
	 this.indexReader = DirectoryReader.open(new SimpleFSDirectory(path));
	 this.searcher = new IndexSearcher(indexReader);
	}
	
	public void search(Double lat, Double lng, int distance) throws IOException{
	 
	 Point p = ctx.makePoint(lat, lng);
	 SpatialArgs args = new SpatialArgs(SpatialOperation.Intersects,
	   ctx.makeCircle(lat, lng, DistanceUtils.dist2Degrees(distance, DistanceUtils.EARTH_EQUATORIAL_RADIUS_MI)));
	 Filter filter = strategy.makeFilter(args);
	 
	 ValueSource valueSource = strategy.makeDistanceValueSource(p);
	 Sort distSort = new Sort(valueSource.getSortField(false)).rewrite(searcher);
	 
	 int limit = 30;
	 TopDocs topDocs = searcher.search(new MatchAllDocsQuery(), filter, limit, distSort);
	 ScoreDoc[] scoreDocs = topDocs.scoreDocs;
	 
	 for(ScoreDoc s: scoreDocs) {
	  
	  Document doc = searcher.doc(s.doc);
	  Point docPoint = (Point) ctx.readShape(doc.get(strategy.getFieldName()));
	  double docDistDEG = ctx.getDistCalc().distance(args.getShape().getCenter(), docPoint);
	  double docDistInKM = DistanceUtils.degrees2Dist(docDistDEG, DistanceUtils.EARTH_EQUATORIAL_RADIUS_MI);
	  //System.out.println(doc.get("id") + "\t" + doc.get("name") + "\t" + docDistInKM + " km ");
	  log.info "Searched "+ doc.get("id") + "\t" + doc.get("name") + "\t" + docDistInKM + " mi "
	  
	 }
	 
	}
    
}
