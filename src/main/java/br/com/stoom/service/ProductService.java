package br.com.stoom.service;

import br.com.stoom.dto.FilterProductDTO;
import br.com.stoom.dto.ProductDTO;
import br.com.stoom.model.Product;

import java.util.List;

public interface ProductService {

    /*
     * Cadastra um novo Product.
     *
     * @param Product, O Product que será criado.
     *
     * @return O Product que foi cadastrado.
     *
     * @throws Se existir um Product com o mesmo nome retorna uma mensagem de erro.
     *
     */

    public ProductDTO create(ProductDTO product);

    /*
     * Atualiza um Product.
     *
     * @param Product, o Product que será atualizado.
     *
     * @param id, O id do Product que será atualizado.
     *
     * @return o Product que foi atualizado.
     *
     */

    public ProductDTO update(ProductDTO product, Long id);

    /*
     * Listar todos os Products
     *
     * @return A lista de todos os Products.
     *
     */
    public List<ProductDTO> findAll(FilterProductDTO filterProductDTO);

    /*
     * Pesquisa um Product pelo ID.
     *
     * @param id, O ID do Product que será pesquisado.
     *
     * @return o Product que foi pesquisado.
     *
     */

    public ProductDTO findById(Long id);

    /*
     * Excluir uma Product pelo ID
     *
     * @param ID, Identificador do Product que será deletado.
     *
     * @return Um boolean confirmando que foi excluido.
     */
    public Boolean inativeOrActive(Long id, boolean inativeOrActive);

}
