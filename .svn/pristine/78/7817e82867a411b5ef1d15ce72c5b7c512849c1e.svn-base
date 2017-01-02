package cn.lyj.core.service.product;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.common.page.Pagination;
import cn.lyj.core.bean.product.Img;
import cn.lyj.core.bean.product.Product;
import cn.lyj.core.bean.product.Sku;
import cn.lyj.core.dao.product.ProductDao;
import cn.lyj.core.query.product.ImgQuery;
import cn.lyj.core.query.product.ProductQuery;

/**
 * 商品事务层
 * 
 * @author lixu
 * @Date [2014-3-27 下午03:31:57]
 */
@Service
@Transactional
public class ProductServiceImpl implements ProductService {

	@Resource
	ProductDao productDao;

	// 这里为什么不注入 Dao ?
	// 因为 service 支持事务，前后 进入一次 aop ，分别是 begin Transaction 和 commit
	// 出错的情况下 则是 rollback 而不是 commoit.
	// 后期手动配置 AOP
	// ，为了分布式缓存，但是为了能够知道商品增删改的时候能够及时更新缓存，在这里使用注入service的方式，因为嵌套在里面，service
	// 本身又支持事务，所以有变化也能够知道。
	// 如果注入的是 Dao,则 商品有变化 此时是不知道的。
	// 目的：让缓存里的数据和数据库里的数据同步
	@Resource
	ImgService imgService;

	@Resource
	SkuService skuService;

	/**
	 * 插入数据库
	 * 
	 * @return
	 */
	public Integer addProduct(Product product) {
		// 商品 sku 图片 保存的顺序观察表结构
		// 商品编号
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String format = df.format(new Date());
		product.setNo(format);
		product.setCreateTime(new Date());

		// 返回影响的行数 返回商品id
		// 商品保存
		Integer i = productDao.addProduct(product);
		// 保存图片
		// 设置图片 商品ID//返回来的id
		product.getImg().setProductId(product.getId());
		// 设置图片是否默认
		product.getImg().setIsDef(1);
		imgService.addImg(product.getImg());

		// 实例化sku
		Sku sku = new Sku();
		// 商品ID
		sku.setProductId(product.getId());
		sku.setDeliveFee(10.00);
		sku.setSkuPrice(0.00);
		sku.setMarketPrice(0.00);
		sku.setStockInventory(0);
		sku.setSkuUpperLimit(0);
		sku.setCreateTime(new Date());
		sku.setLastStatus(1);
		sku.setSkuType(1);
		sku.setSales(0);
		// 保存 sku
		for (String color : product.getColor().split(",")) {
			sku.setColorId(Integer.parseInt(color));
			for (String size : product.getSize().split(",")) {
				sku.setSize(size);
				// 保存
				skuService.addSku(sku);
			}

		}

		return i;
	}

	/**
	 * 根据主键查找
	 */
	@Transactional(readOnly = true)
	public Product getProductByKey(Integer id) {
		Product product = productDao.getProductByKey(id);
		ImgQuery imgQuery = new ImgQuery();
		imgQuery.setProductId(product.getId());
		imgQuery.setIsDef(1);
		List<Img> imgs = imgService.getImgList(imgQuery);
		product.setImg(imgs.get(0));
		
		return product;
	}

	@Transactional(readOnly = true)
	public List<Product> getProductsByKeys(List<Integer> idList) {
		return productDao.getProductsByKeys(idList);
	}

	/**
	 * 根据主键删除
	 * 
	 * @return
	 */
	public Integer deleteByKey(Integer id) {
		return productDao.deleteByKey(id);
	}

	public Integer deleteByKeys(List<Integer> idList) {
		return productDao.deleteByKeys(idList);
	}

	/**
	 * 根据主键更新
	 * 
	 * @return
	 */
	public Integer updateProductByKey(Product product) {
		return productDao.updateProductByKey(product);
	}

	@Transactional(readOnly = true)
	public Pagination getProductListWithPage(ProductQuery productQuery) {
		Pagination p = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(),
				productDao.getProductListCount(productQuery));
		List<Product> products = productDao.getProductListWithPage(productQuery);
		for (Product product : products) {
			ImgQuery imgQuery = new ImgQuery();
			imgQuery.setProductId(product.getId());
			imgQuery.setIsDef(1);
			List<Img> imgs = imgService.getImgList(imgQuery);
			product.setImg(imgs.get(0));
		}
		p.setList(products);
		return p;
	}

	@Transactional(readOnly = true)
	public List<Product> getProductList(ProductQuery productQuery) {
		return productDao.getProductList(productQuery);
	}
}
