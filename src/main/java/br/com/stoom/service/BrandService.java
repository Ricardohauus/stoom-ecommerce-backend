package br.com.stoom.service;

import br.com.stoom.dto.BrandDTO;

import java.util.List;

public interface BrandService {

    /*
     * Cadastra uma nova Brand.
     *
     * @param Brand, A Brand que será criada.
     *
     * @return A Brand que foi cadastrada.
     *
     * @throws Se existir um Brand com o mesmo nome retorna uma mensagem de erro.
     *
     */

    public BrandDTO create(BrandDTO brand);

    /*
     * Atualiza uma Brand.
     *
     * @param Brand, a Brand que será atualizada.
     *
     * @param id, O id da Brand que será atualizado.
     *
     * @return a Brand que foi atualizada.
     *
     */

    public BrandDTO update(BrandDTO brand, Long id);

    /*
     * Listar todos as Brands
     *
     * @return A lista de todas as Brands.
     *
     */
    public List<BrandDTO> findAll();

    /*
     * Pesquisa uma Brand pelo ID.
     *
     * @param id, O ID da Brand que será pesquisada.
     *
     * @return a Brand que foi pesquisada.
     *
     */

    public BrandDTO findById(Long id);

    /*
     * Excluir uma Brand pelo ID
     *
     * @param ID, Identificador da Brand que será deletada.
     *
     * @return Um boolean confirmando que foi excluido.
     */
    public Boolean inativeOrActive(Long id, boolean inativeOrActive);

}
