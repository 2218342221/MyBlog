package com.shawn.blog.service;

import com.shawn.blog.NotFoundException;
import com.shawn.blog.dao.TypeRepository;
import com.shawn.blog.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class TypesServiceImpl implements TypesService {

    @Autowired
    private TypeRepository typeRepository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Transactional
    @Override
    public Type getType(Long id) {
        return typeRepository.findById(id).isPresent()? typeRepository.findById(id).get():null;
    }

    @Transactional
    @Override
    public Type getTypeByName(String name) {
        return typeRepository.findByName(name);
    }

    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Type type1 = typeRepository.findById(id).isPresent()? typeRepository.findById(id).get():null;
        if(type1==null){
            throw new NotFoundException("不存在该类型");
        }
        BeanUtils.copyProperties(type,type1);

        return typeRepository.save(type1);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {

        typeRepository.deleteById(id);

    }
}
