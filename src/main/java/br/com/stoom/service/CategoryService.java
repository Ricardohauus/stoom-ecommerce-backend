package br.com.stoom.service;

import br.com.stoom.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {

    /*
     * Cadastra uma nova Category.
     *
     * @param Category, A Category que será criada.
     *
     * @return A Category que foi cadastrada.
     *
     * @throws Se existir um Category com o mesmo nome retorna uma mensagem de erro.
     *
     */

    public CategoryDTO create(CategoryDTO category);

    /*
     * Atualiza uma Category.
     *
     * @param Category, a Category que será atualizada.
     *
     * @param id, O id da Category que será atualizado.
     *
     * @return a Category que foi atualizada.
     *
     */

    public CategoryDTO update(CategoryDTO category, Long id);

    /*
     * Listar todos as Categorys
     *
     * @return A lista de todas as Categorys.
     *
     */
    public List<CategoryDTO> findAll();

    /*
     * Pesquisa uma Category pelo ID.
     *
     * @param id, O ID da Category que será pesquisada.
     *
     * @return a Category que foi pesquisada.
     *
     */

    public CategoryDTO findById(Long id);

    /*
     * Excluir uma Category pelo ID
     *
     * @param ID, Identificador da Category que será deletada.
     *
     * @return Um boolean confirmando que foi excluido.
     */
    public Boolean inativeOrActive(Long id, boolean inativeOrActive);

}
