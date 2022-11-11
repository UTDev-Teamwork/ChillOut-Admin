package com.utdev.posbackend.service;

import com.utdev.posbackend.model.Articulo;
import com.utdev.posbackend.repository.ArticuloRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ArticuloServiceImpl implements IArticuloService {
    private final ArticuloRepository articuloRepository;

    public ArticuloServiceImpl(ArticuloRepository articuloRepository) {
        this.articuloRepository = articuloRepository;
    }

    // CREATE & UPDATE
    @Override
    public Articulo saveArticulo(Articulo articulo){
        if(articulo.getFecha_registro()==null)
            articulo.setFecha_registro(LocalDateTime.now());
        articulo.setLast_update_inventory(LocalDateTime.now());
        return articuloRepository.save(articulo);
    }

    // READ
    @Override
    public List<Articulo> findAllArticulos(){
        return articuloRepository.findAll();
    }

    @Override
    public Page<Articulo> findTenArticulos(){
        Pageable limit = PageRequest.of(0,10);
        return articuloRepository.findAll(limit);
    }

    @Override
    public Articulo findById(String id){
        return articuloRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Articulo> findPaging(Pageable pageable){
        return articuloRepository.findAll(pageable);
    }

    @Override
    public Page<Articulo> findByNombre(String nombre, Pageable pageable){
        return articuloRepository.findByDescripcionContaining(nombre, pageable);
    }

    @Override
    public Page<Articulo> findByImage(boolean img, Pageable pageable){
        return articuloRepository.findByHasimg(img, pageable);
    }

    // DELETE
    @Override
    public void deleteArticulo(String codBarras){
        articuloRepository.deleteById(codBarras);
    }

    @Override
    public boolean cancelArticulo(String codBarras)
    {
        try {
            articuloRepository.findById(codBarras).ifPresentOrElse(articulo -> {
                if(articulo.isVisible()){
                    articulo.setVisible(false);
                    articulo.setArticulo_disponible(false);
                }else{
                    articulo.setVisible(true);
                    articulo.setArticulo_disponible(true);
                }
                articulo.setLast_update_inventory(LocalDateTime.now());
                if (articuloRepository.save(articulo) == null)
                    throw new RuntimeException("No se pudo actualizar el producto");
            },() -> { throw new RuntimeException("No se encontro el producto especificado"); });
            return true;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public boolean suspendArticulo(String codBarras)
    {
        try {
            articuloRepository.findById(codBarras).ifPresentOrElse(articulo -> {
                if(articulo.isVisible())
                    articulo.setArticulo_disponible(!articulo.isArticulo_disponible());
                else
                    throw new RuntimeException("El articulo esta cancelado");
                articulo.setLast_update_inventory(LocalDateTime.now());
                if (articuloRepository.save(articulo) == null)
                    throw new RuntimeException("No se pudo actualizar el producto");
            },() -> { throw new RuntimeException("No se encontro el producto especificado"); });
            return true;
        }catch (Exception ex){
            System.out.println(ex.getMessage());
            return false;
        }
    }
}
