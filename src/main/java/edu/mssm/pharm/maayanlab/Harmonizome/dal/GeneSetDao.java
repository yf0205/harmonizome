package edu.mssm.pharm.maayanlab.Harmonizome.dal;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import edu.mssm.pharm.maayanlab.Harmonizome.model.GeneSet;
import edu.mssm.pharm.maayanlab.common.database.HibernateUtil;

public class GeneSetDao {
	
	@SuppressWarnings("unchecked")
	public static List<GeneSet> getByWordInAttributeName(String query) {
		String sql = String.format("" +
			"SELECT DISTINCT * FROM gene_set " +
			"WHERE MATCH(gene_set.name_from_dataset) AGAINST('%s*' IN BOOLEAN MODE)",
			query
		);		
		return (List<GeneSet>) HibernateUtil
			.getCurrentSession()
			.createSQLQuery(sql)
			.addEntity(GeneSet.class)
			.list();
	}
	
	@SuppressWarnings("unchecked")
	public static List<GeneSet> getAllFromDatasetName(String name) {
		return (List<GeneSet>) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT geneSet FROM GeneSet AS geneSet " +
				"WHERE geneSet.nameFromDataset = :name"
			)
			.setString("name", name)
			.list();
	}

	public static GeneSet getFromNameAndDataset(String name, String datasetName) {
		return (GeneSet) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT geneSet FROM GeneSet AS geneSet " +
				"JOIN geneSet.dataset AS dataset " +
				"WHERE geneSet.nameFromDataset = :name " +
				"AND dataset.name = :datasetName"
			)
			.setString("name", name)
			.setString("datasetName", datasetName)
			.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public static List<GeneSet> getByWordInAttributeNameButIgnoreExactMatches(String query, List<Integer> idsToIgnore) {
		StringBuilder builder  = new StringBuilder();
		Iterator<Integer> iter = idsToIgnore.iterator();
		builder.append("(");
		while (iter.hasNext()) {
			builder.append(iter.next());
	        if (iter.hasNext()) {
	        	builder.append(",");
	        }
		}
		builder.append(")");
		String sql = String.format("" +
			"SELECT DISTINCT * FROM gene_set " +
			"WHERE MATCH(gene_set.name_from_dataset) AGAINST('%s*' IN BOOLEAN MODE) AND gene_set.id NOT IN %s", 
			query, builder.toString()
		);
		return (List<GeneSet>) HibernateUtil
			.getCurrentSession()
			.createSQLQuery(sql)
			.addEntity(GeneSet.class)
			.list();
	}
	
	public static Pair<List<GeneSet>, List<GeneSet>> getFromGeneAndDataset(String geneSymbol, String datasetName) {
		List<GeneSet> pos = getFromDatasetAndGeneAndValue(datasetName, geneSymbol, 1);
		List<GeneSet> neg = getFromDatasetAndGeneAndValue(datasetName, geneSymbol, -1);
		return new ImmutablePair<List<GeneSet>, List<GeneSet>>(pos, neg);
	}

	@SuppressWarnings("unchecked")
	private static List<GeneSet> getFromDatasetAndGeneAndValue(String datasetName, String geneSymbol, int thresholdValue) {
		return (List<GeneSet>) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT geneSet FROM GeneSet AS geneSet " +
				"JOIN geneSet.features AS feats " +
				"JOIN geneSet.dataset AS dataset " +
				"JOIN feats.gene AS gene " +
				"WHERE dataset.name = :datasetName AND gene.symbol = :geneSymbol AND feats.thresholdValue = :thresholdValue"
			)
			.setString("datasetName", datasetName)
			.setString("geneSymbol", geneSymbol)
			.setInteger("thresholdValue", thresholdValue)
			.list();
	}
}