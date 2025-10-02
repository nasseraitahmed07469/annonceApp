import { Category } from "../enums/Category.enum";

export interface AdResponse {
  id: number;
  title: string;
  description: string;
  price: number;
  category: Category; 
  createdAt: string; 
  updatedAt: string;
  author: string;
  email: string;
  phone: string;
  active: boolean;
}
