import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'src/app/common/product';
import { ProductService } from 'src/app/services/product.service';

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list-grid.component.html',
  styleUrls: ['./product-list.component.css']
})

export class ProductListComponent implements OnInit {
  products: Product[] = [];
  currentCategoryId: number = 1;
  currentCategoryName: string = "";
  searchMode: boolean = false;
  
  constructor(private productService: ProductService, private route: ActivatedRoute) { }
  
  ngOnInit(): void {
    this.route.paramMap.subscribe(() => {
      this.listProducts();
    });
  }

  listProducts() {
    this.searchMode = this.route.snapshot.paramMap.has('keyword');
    if (this.searchMode) {
      this.handleSearchProducts();
    }
    else {
      this.handleListProducts();
    }
  }

  handleSearchProducts() {
    const theKeyword: string = this.route.snapshot.paramMap.get('keyword')!;
    // Search product using keyword
    this.productService.searchProducts(theKeyword).subscribe(
      data => {
        this.products = data;
      }
    )
  }

  handleListProducts() {
    // Check if "if" avaiable
    const hasCategoryId: boolean = this.route.snapshot.paramMap.has('id');
    if (hasCategoryId) {
      // Get 'id', convert string to number using +; ! informs compiler object is not null
      this.currentCategoryId = +this.route.snapshot.paramMap.get('id')!;
      // Get 'name'
      this.currentCategoryName = this.route.snapshot.paramMap.get('name')!;
    }
    else {
      // default to category 1
      this.currentCategoryId = 1;
      // Update this to reflect appropriate name for given category ID if modified
      this.currentCategoryName = 'Books';
    }
    this.productService.getProductList(this.currentCategoryId).subscribe(
        data => {
          this.products = data;
        }
      )
  }


}