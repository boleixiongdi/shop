package com.rt.shop.view.web.tools;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rt.shop.entity.Article;
import com.rt.shop.service.IArticleService;

@Component
public class ArticleViewTools {

	@Autowired
	private IArticleService articleService;

	public Article queryArticle(Long id, int position) {
		// String query =
		// "select obj from Article obj where obj.articleClass.id=:class_id and obj.display=:display and ";
		Article article = this.articleService.selectById(id);
		List<Article> objs = null;
		if (article != null) {
			
			if (position > 0) {
				
				String sql="where articleClass_id="+article.getArticleClass_id()+" and display="+Boolean.valueOf(true)+" and addTime>'"+article.getAddTime()+"' order by addTime desc";
				objs = this.articleService.selectList(sql, null);
				
			} else {
				String sql="where articleClass_id="+article.getArticleClass_id()+" and display="+Boolean.valueOf(true)+" and addTime<'"+article.getAddTime()+"' order by addTime desc";
				objs = this.articleService.selectList(sql, null);
			}
		}

		if (objs.size() > 0) {
			return (Article) objs.get(0);
		}
		Article obj = new Article();
		obj.setTitle("没有了");
		return obj;
	}

}
