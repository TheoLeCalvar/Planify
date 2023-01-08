import { ComponentFixture, TestBed } from '@angular/core/testing';

import { RtafFormComponent } from './rtaf-form.component';

describe('RtafFormComponent', () => {
  let component: RtafFormComponent;
  let fixture: ComponentFixture<RtafFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ RtafFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(RtafFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
