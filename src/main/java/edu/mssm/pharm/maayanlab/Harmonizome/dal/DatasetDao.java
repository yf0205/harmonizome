package edu.mssm.pharm.maayanlab.Harmonizome.dal;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import edu.mssm.pharm.maayanlab.Harmonizome.model.Dataset;
import edu.mssm.pharm.maayanlab.common.database.HibernateUtil;

public class DatasetDao {

	public static Dataset getFromName(String name) {
		Criteria criteria = HibernateUtil.getCurrentSession()
			.createCriteria(Dataset.class)
			.add(Restrictions.eq("name", name).ignoreCase());
		return (Dataset) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public static List<Dataset> getByGene(String geneSymbol) {
		return (List<Dataset>) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT DISTINCT dataset FROM Dataset AS dataset " +
				"JOIN dataset.geneSets AS geneSets " +
				"JOIN geneSets.features as feats " +
				"JOIN feats.gene AS gene " +
				"WHERE gene.symbol = :symbol"
			)
			.setString("symbol", geneSymbol)
			.list();
	}

	public static Dataset getByAttribute(String attributeName) {
		return (Dataset) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT DISTINCT dataset FROM Dataset AS dataset " +
				"JOIN dataset.attributes AS attrs " +
				"WHERE attrs.nameFromDataset = :name"
			)
			.setString("name", attributeName)
			.uniqueResult();
	}
	
	public static Long getCountGeneAttributeAssocations(String datasetName) {
		return (Long) HibernateUtil
			.getCurrentSession()
			.createQuery(
				"SELECT COUNT(feat) FROM Feature AS feat " +
				"JOIN feat.geneSet AS geneSet " +
				"JOIN geneSet.dataset AS dataset " +
				"WHERE dataset.name = :datasetName"
			)
			.setString("datasetName", datasetName)
			.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public static List<Dataset> getByWordInResourceName(String query) {
		String sql = String.format("SELECT * FROM dataset JOIN resource ON dataset.resource_fk = resource.id WHERE MATCH(resource.name) AGAINST('%s*' IN BOOLEAN MODE)", query);		
		return (List<Dataset>) HibernateUtil
			.getCurrentSession()
			.createSQLQuery(sql)
			.addEntity(Dataset.class)
			.list();
	}
}
