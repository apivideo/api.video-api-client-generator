
using System;
using System.Collections;
using System.Collections.Generic;
using VideoApiClient.Model;

/// <summary>
/// Handle T paginated items
/// </summary>
/// <typeparam name="T">The class to paginate</typeparam>
public class Page<T> {
    /// <summary>
    /// Current page items
    /// </summary>
    public List<T> Items { get; }
    /// <summary>
    /// Current page number
    /// </summary>
    public int CurrentPage { get; }
    /// <summary>
    /// Page's size
    /// </summary>
    public int PageSize { get; }
    /// <summary>
    /// Total pages count
    /// </summary>
    public int PagesTotal { get; }
    /// <summary>
    /// Total items count
    /// </summary>
    public int ItemsTotal { get; }
    /// <summary>
    /// Current page items count
    /// </summary>
    public int CurrentPageItems { get; }
    /// <summary>
    /// List of paginations links
    /// </summary>
    public List<PaginationLink> Links { get; }
    /// <summary>
    /// Returns the next page Page item
    /// </summary>
    public Func<Page<T>> GetNextPage { get; }
    private Page<T> that;

    /// <summary>
    /// Creates an instance of the page class
    /// </summary>
    /// <param name="items">List of current page items</param>
    /// <param name="pagination">Pagination information</param>
    /// <param name="getNextPage">Func to get next page data</param>
    public Page(List<T> items, Pagination pagination, Func<Page<T>> getNextPage) {
        this.Items = items;
        if (pagination != null) {
            this.CurrentPage = pagination.currentpage;
            this.PageSize = pagination.pagesize;
            this.PagesTotal = pagination.pagestotal;
            this.ItemsTotal = pagination.itemstotal;
            this.CurrentPageItems = pagination.currentpageitems;
            this.Links = pagination.links;
            this.GetNextPage = getNextPage;
            this.that = this;
        }
    }
}
