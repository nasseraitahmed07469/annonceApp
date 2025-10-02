import { Category } from "../enums/Category.enum";

export interface AdRequest {
  title: string;
  description: string;
  price: number;
  category: Category;
  author: string;
  email: string;
  phone: string;
}
