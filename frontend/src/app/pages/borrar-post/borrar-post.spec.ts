import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BorrarPost } from './borrar-post';

describe('BorrarPost', () => {
  let component: BorrarPost;
  let fixture: ComponentFixture<BorrarPost>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [BorrarPost],
    }).compileComponents();

    fixture = TestBed.createComponent(BorrarPost);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
