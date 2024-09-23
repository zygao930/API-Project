//package com.project.project.service.EntityService;
//
//import java.io.Serializable;
//import java.util.List;
//
//import com.project.project.entity.Order;
//import com.project.project.entity.Product;
//import com.project.project.entity.User;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//
//public interface BaseService <T, I extends Serializable> {
//    /**
//     * @param id id
//     * @return T
//     */
//    T find(I id);
//
//    /**
//     * @return List
//     */
//    List<T> findAll();
//
//    /**
//     * @param ids ids
//     * @return List
//     */
//    List<T> findList(I[] ids);
//
//    /**
//     * @param ids ids
//     * @return List
//     */
//    List<T> findList(Iterable<I> ids);
//
//    /**
//     * @param pageable pageable
//     * @return Page
//     */
//    Page<T> findAll(Pageable pageable);
//
//    /**
//     * @param spec     spec
//     * @param pageable pageable
//     * @return Page
//     */
//    Page<T> findAll(Specification<T> spec, Pageable pageable);
//
//    /**
//     * @param spec spec
//     * @return T
//     */
//    T findOne(Specification<T> spec);
//
//    /**
//     * count.
//     *
//     * @return long
//     */
//    long count();
//
//    /**
//     * count.
//     *
//     *
//     * @param spec spec
//     * @return long
//     */
//    long count(Specification<T> spec);
//
//    /**
//     * exists.
//     *
//     * @param id id
//     * @return boolean
//     */
//    boolean exists(I id);
//
//    /**
//     * save.
//     *
//     * @param entity
//     * @return
//     */
//
//    void save(T entity);
//
//    /**
//     * save.
//     *
//     * @param users
//     */
//    void save(List<T> users);
//
//    /**
//     * update.
//     *
//     * @param entity entity
//     * @return T
//     */
//    void update(T entity);
//
//
//    /**
//     * delete.
//     *
//     * @param id id
//     */
//    void delete(I id);
//
//    /**
//     * delete by ids.
//     *
//     * @param ids ids
//     */
//    void deleteByIds(List<I> ids);
//
//    /**
//     * delete.
//     *
//     * @param entities entities
//     */
//    void delete(T[] entities);
//
//    /**
//     * delete.
//     *
//     * @param entities entities
//     */
//    void delete(Iterable<T> entities);
//
//    /**
//     * delete.
//     *
//     * @param entity entity
//     */
//    void delete(T entity);
//
//    /**
//     * delete all.
//     */
//    void deleteAll();
//
//    /**
//     * find list.
//     *
//     * @param spec spec
//     * @return list
//     */
//    List<T> findList(Specification<T> spec);
//
//    /**
//     * find list.
//     *
//     * @param spec spec
//     * @param sort sort
//     * @return List
//     */
//    List<T> findList(Specification<T> spec, Sort sort);
//
//    /**
//     * flush.
//     */
//    void flush();
//}